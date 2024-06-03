package com.example.personalcolor;

//사용자 계정 정의
public class IdPassword
{
    private static String ID;
    private static String Password;
    private static String Name;
    private static String Gender;
    private static String Address;
    //ID/Password 지정하기
    public void setIdPassword(String[] information){
        ID = information[0];
        Password = information[1];
        Name = information[2];
        Gender = information[3];
        Address = information[4];
    }

    //ID 가져오기
    public String getID(){return ID;}

    //Password 가져오기
    public  String getPassword(){return Password;}

    //Name 가져오기
    public  String getName(){return Name;}

    //Gender 가져오기
    public  String getGender(){return Gender;}

    //Address 가져오기
    public  String getAddress(){return Address;}
}
