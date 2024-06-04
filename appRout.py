from itertools import compress
from urllib import request

import cv2
import dlib
import numpy as np
from colormath.color_conversions import convert_color
from colormath.color_objects import LabColor, sRGBColor, HSVColor
from flask import Flask, request, jsonify
from imutils import face_utils
from sklearn.cluster import KMeans
from werkzeug.utils import secure_filename

app = Flask(__name__)

def apply_kmeans(image, clusters=3):

    img = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    IMAGE = img.reshape((img.shape[0] * img.shape[1], 3))

    # k-means를 사용하여 픽셀을 클러스터링
    kmeans = KMeans(n_clusters=clusters)
    kmeans.fit(IMAGE)

    # 클러스터 중심은 주요 색상
    COLORS = kmeans.cluster_centers_
    LABELS = kmeans.labels_
    numLabels = np.arange(0, clusters + 1)

    # 레이블의 빈도수를 계산
    (hist, _) = np.histogram(LABELS, bins=numLabels)
    hist = hist.astype("float")
    hist /= hist.sum()

    colors = COLORS
    # 빈도수에 따라 내림차순으로 색상을 정렬
    colors = colors[(-hist).argsort()]
    hist = hist[(-hist).argsort()]
    for i in range(clusters):
        colors[i] = colors[i].astype(int)
    # 파란색 마스크를 제거
    fil = [colors[i][2] < 250 and colors[i][0] > 10 for i in range(clusters)]
    colors = list(compress(colors, fil))  # -- 주요 색상 추출

    chart = np.zeros((50, 500, 3), np.uint8)
    start = 0
    chart.fill(255)
    # 색상 사각형을 생성
    for i in range(len(colors)):
        end = start + hist[i] * 500
        r, g, b = colors[i]
        # 색상을 플로팅
        cv2.rectangle(chart, (int(start), 0), (int(end), 50), (r, g, b), -1)
        start = end

    return colors

def extract_face_part(img, face_part_points):
    (x, y, w, h) = cv2.boundingRect(face_part_points)
    crop = img[y:y+h, x:x+w]
    adj_points = np.array([np.array([p[0]-x, p[1]-y]) for p in face_part_points])

    # Create an mask
    mask = np.zeros((crop.shape[0], crop.shape[1]))
    cv2.fillConvexPoly(mask, adj_points, 1)
    mask = mask.astype(bool)
    crop[np.logical_not(mask)] = [255, 0, 0]

    return crop

def is_warm(lab_b, a):
    # standard of skin, eye, eyebrow
    warm_b_std = [11.6518, 3.6484, 11.71445]
    cool_b_std = [4.64255, 0.18735, 4.86635]

    warm_dist = 0
    cool_dist = 0

    body_part = ['skin', 'eye', 'eyebrow']
    for i in range(3):
        warm_dist += abs(lab_b[i] - warm_b_std[i]) * a[i]
        cool_dist += abs(lab_b[i] - cool_b_std[i]) * a[i]
    if(warm_dist <= cool_dist):
        return 1 #warm
    else:
        return 0 #cool

def is_spr(hsv_s, a):
    # standard of skin, eye, eyebrow
    spr_s_std = [18.59296, 25.80645, 30.30303]
    fal_s_std = [27.13987, 37.5, 39.75155]

    spr_dist = 0
    fal_dist = 0

    body_part = ['skin', 'eye', 'eyebrow']
    for i in range(3):
        spr_dist += abs(hsv_s[i] - spr_s_std[i]) * a[i]
        fal_dist += abs(hsv_s[i] - fal_s_std[i]) * a[i]

    if(spr_dist <= fal_dist):
        return 1 #spring
    else:
        return 0 #fall

def is_smr(hsv_s, a):
    # standard of skin, eye, eyebrow
    smr_s_std = [12.5, 24.77064, 21.7195]
    wnt_s_std = [16.73913, 31.3726, 24.8276]

    smr_dist = 0
    wnt_dist = 0

    body_part = ['skin', 'eye', 'eyebrow']
    for i in range(2):
        smr_dist += abs(hsv_s[i] - smr_s_std[i]) * a[i]
        wnt_dist += abs(hsv_s[i] - wnt_s_std[i]) * a[i]

    if(smr_dist <= wnt_dist):
        return 1 #summer
    else:
        return 0 #winter

# 사진을 받아서 퍼스널 컬러 분석하여 보내주기
@app.route('/', methods=['GET','POST'])
def pdt():
    f = request.files['image']
    img_path = "./personal/" + f.filename
    print(img_path)
    f.save("./personal/" + secure_filename(f.filename))

    # dlib의 얼굴 검출기 초기화
    detector = dlib.get_frontal_face_detector()
    # dlib의 얼굴 특징점 추출기 초기화
    predictor = dlib.shape_predictor("models/shape_predictor_68_face_landmarks.dat")

    # 이미지 불러오기
    img = cv2.imread(img_path)

    face_parts = [[], [], [], [], [], [], []]

    # 이미지를 흑백으로 변환
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # 얼굴 검출
    try:
        faces = detector(gray, 1)[0]
    except:
        print("얼굴이 검출되지 않았습니다.")
        tone = 'no'
        return jsonify(tone)

    shape = predictor(cv2.cvtColor(img, cv2.COLOR_BGR2GRAY), faces)
    shape = face_utils.shape_to_np(shape)

    # loop over the face parts individually
    for (name, (i, j)) in face_utils.FACIAL_LANDMARKS_IDXS.items():
        if (name == "right_eyebrow"):
            face_parts[0] = shape[17:22]
        elif (name == "left_eyebrow"):
            face_parts[1] = shape[22:27]
        elif (name == "right_eye"):
            face_parts[2] = shape[36:42]
        elif (name == "left_eye"):
            face_parts[3] = shape[42:48]

    right_eyebrow = extract_face_part(img, face_parts[0])
    left_eyebrow = extract_face_part(img, face_parts[1])
    right_eye = extract_face_part(img, face_parts[2])
    left_eye = extract_face_part(img, face_parts[3])
    # Cheeks are detected by relative position to the face landmarks
    left_cheek = img[shape[29][1]:shape[33][1], shape[4][0]:shape[48][0]]
    right_cheek = img[shape[29][1]:shape[33][1], shape[54][0]:shape[12][0]]

    face = [left_cheek, right_cheek,
            left_eye, right_eye,
            left_eyebrow, right_eyebrow]

    temp = []
    for f in face:
        dc = apply_kmeans(f, 5)
        temp.append(np.array(dc[0]))
    cheek = np.mean([temp[0], temp[1]], axis=0)
    eye = np.mean([temp[2], temp[3]], axis=0)
    eyebrow = np.mean([temp[3], temp[4]], axis=0)

    Lab_b, hsv_s = [], []
    color = [cheek, eye, eyebrow]
    for i in range(3):
        rgb = sRGBColor(color[i][0], color[i][1], color[i][2], is_upscaled=True)
        lab = convert_color(rgb, LabColor, through_rgb_type=sRGBColor)
        hsv = convert_color(rgb, HSVColor, through_rgb_type=sRGBColor)
        Lab_b.append(float(format(lab.lab_b, ".2f")))
        hsv_s.append(float(format(hsv.hsv_s, ".2f")) * 100)

    Lab_weight = [30, 5, 20]
    hsv_weight = [10, 1, 1]

    if (is_warm(Lab_b, Lab_weight)):
        if (is_spr(hsv_s, hsv_weight)):
            tone = 'spring'
        else:
            tone = 'fall'
    else:
        if (is_smr(hsv_s, hsv_weight)):
            tone = 'summer'
        else:
            tone = 'winter'

    # Print Result
    return jsonify(tone)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port= 8080, debug=True)