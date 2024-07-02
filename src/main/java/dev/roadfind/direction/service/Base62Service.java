package dev.roadfind.direction.service;


import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Service;

@Service
public class Base62Service {

    private static final Base62 BASE62_INSTANCE = Base62.createInstance();

    public String encodeDirectionId(Long directionId) {
        return new String(BASE62_INSTANCE.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId){
        String resultDirectionId = new String(BASE62_INSTANCE.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }

}
