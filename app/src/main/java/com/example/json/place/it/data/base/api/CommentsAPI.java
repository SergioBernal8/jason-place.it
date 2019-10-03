package com.example.json.place.it.data.base.api;


import com.example.json.place.it.domain.model.Comment;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentsAPI {

    @GET(APIConstants.PATH_POST_COMMENTS)
    Call<List<Comment>> getCommentsForPost(@Query("postId") int postId);
}
