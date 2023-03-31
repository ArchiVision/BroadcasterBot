package com.pathz.broadcaster.postsource;

import com.pathz.broadcaster.postgather.CnnPostGather;
import com.pathz.broadcaster.postgather.PostGather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CnnPostSource extends AbstractPostSource {
    private final CnnPostGather cnnPostGather;

    @Override
    public PostGather getPostGather() {
        return cnnPostGather;
    }
}
