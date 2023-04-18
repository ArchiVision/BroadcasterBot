package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.postgather.PostGather;
import com.archivision.broadcaster.postgather.WiredPostGather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.archivision.broadcaster.postsource.PostSource.WIRED;

@Service
@RequiredArgsConstructor
public class WiredPostSource extends AbstractPostSource {
    private final WiredPostGather wiredPostGather;

    @Override
    public PostGather getPostGather() {
        return wiredPostGather;
    }

    @Override
    public String getPostSourceName() {
        return WIRED.getSourceName();
    }
}
