package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoClientImpl implements VoClient{

    @Override
    public R deleteVideo(String videoId) {
        return R.fail().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videIdList) {
        return R.fail().message("time out");
    }
}
