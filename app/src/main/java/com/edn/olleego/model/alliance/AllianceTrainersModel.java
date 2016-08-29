package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 9..
 */
public class AllianceTrainersModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private int _id;
        private String name;
        private String gender;
        private String avatar;
        private BasicInfo basic_info;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public BasicInfo getBasic_info() {
            return basic_info;
        }

        public void setBasic_info(BasicInfo basic_info) {
            this.basic_info = basic_info;
        }

        public class BasicInfo{
            private Career career;
            private String tel;

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public Career getCareer() {
                return career;
            }

            public void setCareer(Career career) {
                this.career = career;
            }

            public class Career{
                private List<Professional> professional = new ArrayList<>();
                private List<Main> main = new ArrayList<>();
                private List<String> license = new ArrayList<>();
                private List<String> activity = new ArrayList<>();

                public class Professional{
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
                public class Main{
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

                public List<Professional> getProfessional() {
                    return professional;
                }

                public void setProfessional(List<Professional> professional) {
                    this.professional = professional;
                }

                public List<Main> getMain() {
                    return main;
                }

                public void setMain(List<Main> main) {
                    this.main = main;
                }

                public List<String> getLicense() {
                    return license;
                }

                public void setLicense(List<String> license) {
                    this.license = license;
                }

                public List<String> getActivity() {
                    return activity;
                }

                public void setActivity(List<String> activity) {
                    this.activity = activity;
                }
            }
        }
    }

}
