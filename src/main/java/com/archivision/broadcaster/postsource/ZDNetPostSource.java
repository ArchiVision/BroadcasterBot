package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.postgather.PostGather;
import com.archivision.broadcaster.postgather.ZDNetPostGather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.archivision.broadcaster.postsource.PostSource.ZD_NET;

@Service
@RequiredArgsConstructor
public class ZDNetPostSource extends AbstractPostSource {
    private final ZDNetPostGather ZDNetPostGather;

    @Override
    public PostGather getPostGather() {
        return ZDNetPostGather;
    }

    @Override
    public String getPostSourceName() {
        return ZD_NET.getSourceName();
    }
}
