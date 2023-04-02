package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.postgather.CnnPostGather;
import com.archivision.broadcaster.postgather.PostGather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CnnPostSource extends AbstractPostSource {
    private final CnnPostGather cnnPostGather;

    @Override
    public PostGather getPostGather() {
        return cnnPostGather;
    }
}
