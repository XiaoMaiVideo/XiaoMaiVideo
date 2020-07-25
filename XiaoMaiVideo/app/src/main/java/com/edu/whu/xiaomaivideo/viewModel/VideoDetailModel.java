/**
 * Author:
 * Create Time:
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.Movie;

/**
 * Author: 李季东 张俊杰 叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/16
 */
public class VideoDetailModel extends ViewModel {
    private MutableLiveData<Movie> movie;

    public VideoDetailModel(){
         movie = new MutableLiveData<>();
    }

    public LiveData<Movie> getMovie() {return movie;}

    public void setMovie(Movie mMovie) {
        this.movie.setValue(mMovie);
    }
}
