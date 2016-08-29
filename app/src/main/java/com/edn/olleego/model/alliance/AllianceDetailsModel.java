package com.edn.olleego.model.alliance;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 8..
 */
public class AllianceDetailsModel implements Parcelable {
    protected AllianceDetailsModel(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllianceDetailsModel> CREATOR = new Creator<AllianceDetailsModel>() {
        @Override
        public AllianceDetailsModel createFromParcel(Parcel in) {
            return new AllianceDetailsModel(in);
        }

        @Override
        public AllianceDetailsModel[] newArray(int size) {
            return new AllianceDetailsModel[size];
        }
    };

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private int _id;
        private float point_avg;
        private int review_count;
        private boolean sale_flag;

        private CenterDinfo center_dinfo;
        private CenterBinfo center_binfo;

        public class CenterDinfo{
            private String description;
            private List<String> image = new ArrayList();
            private List<FacilitiesInfo> facilities_info = new ArrayList();
            private OpTime optime;

            public class FacilitiesInfo{
                private int _id;
                private String value;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public class OpTime{
                private String start;
                private String end;

                public String getStart() {
                    return start;
                }

                public void setStart(String start) {
                    this.start = start;
                }

                public String getEnd() {
                    return end;
                }

                public void setEnd(String end) {
                    this.end = end;
                }
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getImage() {
                return image;
            }

            public void setImage(List<String> image) {
                this.image = image;
            }

            public List<FacilitiesInfo> getFacilities_info() {
                return facilities_info;
            }

            public void setFacilities_info(List<FacilitiesInfo> facilities_info) {
                this.facilities_info = facilities_info;
            }

            public OpTime getOptime() {
                return optime;
            }

            public void setOptime(OpTime optime) {
                this.optime = optime;
            }
        }

        public class CenterBinfo{
            private String name;
            private String place;
            private String locate;
            private String optime;
            private String image;
            private Type type;
            private Tel tel;
            private Address address;
            private Loc loc;
            private Daum daum;
            private List<ClassesInfo> classes = new ArrayList();

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getLocate() {
                return locate;
            }

            public void setLocate(String locate) {
                this.locate = locate;
            }

            public String getOptime() {
                return optime;
            }

            public void setOptime(String optime) {
                this.optime = optime;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Type getType() {
                return type;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public Tel getTel() {
                return tel;
            }

            public void setTel(Tel tel) {
                this.tel = tel;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            public Loc getLoc() {
                return loc;
            }

            public void setLoc(Loc loc) {
                this.loc = loc;
            }

            public Daum getDaum() {
                return daum;
            }

            public void setDaum(Daum daum) {
                this.daum = daum;
            }

            public List<ClassesInfo> getClasses() {
                return classes;
            }

            public void setClasses(List<ClassesInfo> classes) {
                this.classes = classes;
            }

            public class Daum{
                private List<Double> coordinates = new ArrayList();

                public List<Double> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<Double> coordinates) {
                    this.coordinates = coordinates;
                }
            }

            public class Type{
                private int _id;
                private String value;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
            public class Tel{
                private String sub1;
                private String sub2;
                private String sub3;

                public String getSub1() {
                    return sub1;
                }

                public void setSub1(String sub1) {
                    this.sub1 = sub1;
                }

                public String getSub2() {
                    return sub2;
                }

                public void setSub2(String sub2) {
                    this.sub2 = sub2;
                }

                public String getSub3() {
                    return sub3;
                }

                public void setSub3(String sub3) {
                    this.sub3 = sub3;
                }
            }
            public class Address{
                private String sub1;
                private String sub2;

                public String getSub1() {
                    return sub1;
                }

                public void setSub1(String sub1) {
                    this.sub1 = sub1;
                }

                public String getSub2() {
                    return sub2;
                }

                public void setSub2(String sub2) {
                    this.sub2 = sub2;
                }
            }
            public class Loc{
                private String type;
                private List<Double> coordinates = new ArrayList();

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<Double> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<Double> coordinates) {
                    this.coordinates = coordinates;
                }
            }
            public class ClassesInfo{
                private int _id;
                private String value;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public float getPoint_avg() {
            return point_avg;
        }

        public void setPoint_avg(float point_avg) {
            this.point_avg = point_avg;
        }

        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;
        }

        public boolean isSale_flag() {
            return sale_flag;
        }

        public void setSale_flag(boolean sale_flag) {
            this.sale_flag = sale_flag;
        }

        public CenterDinfo getCenter_dinfo() {
            return center_dinfo;
        }

        public void setCenter_dinfo(CenterDinfo center_dinfo) {
            this.center_dinfo = center_dinfo;
        }

        public CenterBinfo getCenter_binfo() {
            return center_binfo;
        }

        public void setCenter_binfo(CenterBinfo center_binfo) {
            this.center_binfo = center_binfo;
        }
    }
}
