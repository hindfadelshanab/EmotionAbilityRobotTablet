package com.nuwa.robot.r2022.emotionalabilityrobot.model;

import android.os.Parcel;

import java.util.List;


public class AnswerContent  {

    private int answerWay; // true/false - select - drag - select character , selectEmotionTable(5)
    private int dragBy; // size - kind - steps

    private boolean isHaveImageForShow;
    private String ImageForShow;
    private Kind kind;
    private List<ImageOption> imageOptions;

    protected AnswerContent(Parcel in) {
        answerWay = in.readInt();
        dragBy = in.readInt();

        isHaveImageForShow = in.readByte() != 0;
        ImageForShow = in.readString();
        in.readList(imageOptions, ImageOption.class.getClassLoader());
//        kind =  in.readParcelable(Kind.class.getClassLoader());


    }

    public AnswerContent() {
    }


    public int getDragBy() {
        return dragBy;
    }

    public void setDragBy(int dragBy) {
        this.dragBy = dragBy;
    }

    public boolean isHaveImageForShow() {
        return isHaveImageForShow;
    }

    public void setHaveImageForShow(boolean haveImageForShow) {
        isHaveImageForShow = haveImageForShow;
    }

    public String getImageForShow() {
        return ImageForShow;
    }

    public void setImageForShow(String imageForShow) {
        ImageForShow = imageForShow;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public List<ImageOption> getImageOptions() {
        return imageOptions;
    }


    public void setImageOptions(List<ImageOption> imageOptions) {
        this.imageOptions = imageOptions;
    }

    public int getAnswerWay() {
        return answerWay;
    }

    public void setAnswerWay(int answerWay) {
        this.answerWay = answerWay;
    }


}




