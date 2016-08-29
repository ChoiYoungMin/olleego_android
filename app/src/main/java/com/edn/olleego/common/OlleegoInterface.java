package com.edn.olleego.common;

import com.edn.olleego.model.alliance.AllianceCategoryModel;
import com.edn.olleego.model.alliance.AllianceDeleteReviewBody;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceEditReviewBody;
import com.edn.olleego.model.alliance.AllianceIsPurchasedResponse;
import com.edn.olleego.model.alliance.AllianceLocationModel;
import com.edn.olleego.model.alliance.AllianceMapItemModel;
import com.edn.olleego.model.alliance.AllianceMapModel;
import com.edn.olleego.model.alliance.AlliancePTListModel;
import com.edn.olleego.model.alliance.AlliancePurchaseIamportRequestBody;
import com.edn.olleego.model.alliance.AlliancePurchaseInfoBody;
import com.edn.olleego.model.alliance.AlliancePurchaseInfoResponse;
import com.edn.olleego.model.alliance.AlliancePurchasePaymentsBody;
import com.edn.olleego.model.alliance.AllianceReviewModel;
import com.edn.olleego.model.alliance.AllianceTrainersModel;
import com.edn.olleego.model.alliance.AllianceWriteReviewBody;
import com.edn.olleego.model.alliance.AllianceWriteReviewResult;
import com.edn.olleego.model.mycenter.ConsultDetailsModel;
import com.edn.olleego.model.mycenter.MyReviewListModel;
import com.edn.olleego.model.mycenter.PurchaseListModel;
import com.edn.olleego.model.mycenter.RecommendedFitnessModel;
import com.edn.olleego.model.mycenter.ReservationDetailsModel;
import com.edn.olleego.model.mycenter.ReservationModel;
import com.edn.olleego.model.video.VideoDetailsModel;
import com.edn.olleego.model.video.VideoListModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kym on 2016. 8. 18..
 */
public interface OlleegoInterface {

    //My센터 추천 피트니스 상품
    @GET("/api/v2/mycenters/pt/recommend")
    Call<RecommendedFitnessModel> getRecommend(@Header("Authorization") String token);

    //My센터 예약현황
    @GET("/api/v2/mycenters/reservation")
    Call<ReservationModel> getReservation(@Header("Authorization") String token);

    //My센터 예약내역 상세
    @GET("/api/v2/reservations/{reserveId}")
    Call<ReservationDetailsModel> getReservationDetails(@Header("Authorization") String token,
                                                        @Path("reserveId") int reserveId,
                                                        @Query("type") String type);

    //My센터 상담내역 상세
    @GET("/api/v2/reservations/{reserveId}")
    Call<ConsultDetailsModel> getConsultDetails(@Header("Authorization") String token,
                                                @Path("reserveId") int reserveId,
                                                @Query("type") String type);

    //My센터 구매내역
    @GET("/api/v2/purchases")
    Call<PurchaseListModel> getPurchaseList(@Header("Authorization") String token);

    //My센터 내가 쓴 리뷰
    @GET("/api/v2/reviews")
    Call<MyReviewListModel> getMyReviewList(@Header("Authorization") String token);

    //제휴센터 상단 탭 카테고 리스트
    @GET("/api/v2/codes/center/type")
    Call<AllianceCategoryModel> getAllianceCategoryList(@Query("sort") String key);

    //센터 위치지정 리스트 (시, 구)
    @GET("/api/v2/centers/sido/sigungu")
    Call<AllianceLocationModel> getCenterLocation();

    //제휴센터 상세 페이지
    @GET("/api/v2/centers/{id}")
    Call<AllianceDetailsModel> getAllianceDetails(@Path("id") int id);

    //제휴센터 pt권 조회
    @GET("/api/v2/pts/center/{id}")
    Call<AlliancePTListModel> getPTList(@Path("id") int id);

    //제휴센터 구매 > 결제하기
    @Headers("Content-Type: application/json")
    @POST("/api/v2/purchases")
    Call<AlliancePurchaseInfoResponse> sendPurchaseInfo(@Header("Authorization") String token,
                                                        @Body AlliancePurchaseInfoBody body);

    //제휴센터 구매 > 결제하기 이후 iamport 호출
    @Headers("Content-Type: application/json")
    @POST("/api/v2/payments/iamport")
    Call<ResponseBody> iamport(@Header("Authorization") String token,
                               @Body AlliancePurchaseIamportRequestBody body);

    //제휴센터 PT권 구매 > 결제 성공시 호출
    @Headers("Content-Type: application/json")
    @POST("/api/v2/payments")
    Call<ResponseBody> payments(@Header("Authorization") String token,
                               @Body AlliancePurchasePaymentsBody body);

    //트레이너 리스트
    @GET("/api/v2/trainers/center/{id}")
    Call<AllianceTrainersModel> getTrainersList(@Path("id") int id);

    //리뷰 리스트
    @GET("/api/v2/reviews/center/{id}/{index}")
    Call<AllianceReviewModel> getReviewList(@Path("id") int id,
                                            @Path("index") int index);

    //센터 구매여부 체크
    @GET("/api/v2/purchases/center/{centerId}")
    Call<AllianceIsPurchasedResponse> isPurchase(@Header("Authorization") String token,
                                                 @Path("centerId") int centerId);

    //리뷰 작성
    @Headers("Content-Type: application/json")
    @POST("/api/v2/reviews")
    Call<AllianceWriteReviewResult> sendReview(@Header("Authorization") String token,
                                               @Body AllianceWriteReviewBody body);

    //리뷰 수정
    @Headers("Content-Type: application/json")
    @PUT("/api/v2/reviews/{id}")
    Call<AllianceWriteReviewResult> editReview(@Header("Authorization") String token,
                                               @Path("id") int id,
                                               @Body AllianceEditReviewBody body);

    //리뷰 삭제
    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "/api/v2/reviews/{id}", hasBody = true)
    Call<AllianceWriteReviewResult> deleteReview(@Header("Authorization") String token,
                                                 @Path("id") int id,
                                                 @Body AllianceDeleteReviewBody body);

    //전체 맵
    @GET("/api/v2/centers/map/{lon}/{lat}/50")
    Call<AllianceMapModel> getAllianceMap(@Path("lon") double lon,
                                          @Path("lat") double lat);

    //센터 카테고리별 맵
    @GET("/api/v2/centers/map/{lon}/{lat}/50")
    Call<AllianceMapModel> getAllianceMap(@Path("lon") double lon,
                                          @Path("lat") double lat,
                                          @Query("filter") String id);

    //마커 클릭시 센터 세부정보
    @GET("/api/v2/centers/{id}/map/{lon}/{lat}")
    Call<AllianceMapItemModel> getCenterInfo(@Path("id") String id,
                                             @Path("lon") double lon,
                                             @Path("lat") double lat);

    //좌측 메뉴 > 운동영상 > 비디오 리스트
    @GET("/api/v2/exdetails/movie/{id}")
    Call<VideoListModel> getVideoList(@Path("id") int id);

    //좌측 메뉴 > 운동영상 > 부위별 근력 카테고리 >  부위 리스트
    @GET("/api/v2/exdetails/movie/2")
    Call<VideoListModel> getPartList(@Query("filter") String id);

    //좌측 메뉴 > 운동영상 > 상세운동 페이지
    @GET("/api/v2/exdetails/{id}")
    Call<VideoDetailsModel> getVideo(@Path("id") String id);
}
