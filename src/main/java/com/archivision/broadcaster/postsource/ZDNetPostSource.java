package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.postgather.PostGather;
import com.archivision.broadcaster.postgather.ZDNetPostGather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZDNetPostSource extends AbstractPostSource {
    private final ZDNetPostGather ZDNetPostGather;

    @Override
    public PostGather getPostGather() {
        return ZDNetPostGather;
    }
}
