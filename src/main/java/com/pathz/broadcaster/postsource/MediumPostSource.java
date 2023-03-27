package com.pathz.broadcaster.postsource;

import com.pathz.broadcaster.postgather.MediumPostGather;
import com.pathz.broadcaster.postgather.PostGather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediumPostSource extends AbstractPostSource {

    @Override
    public PostGather getPostGather() {
        return new MediumPostGather();
    }
}
