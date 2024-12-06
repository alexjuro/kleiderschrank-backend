package alexjuro.de.kleiderschrank.auth;

import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FirebaseTokenCache {
    private final ConcurrentHashMap<String, CachedToken> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public FirebaseTokenCache() {
        // Leere die Map alle 3 Stunden
        scheduler.scheduleAtFixedRate(this::clearCache, 0, 3, TimeUnit.HOURS);
    }

    private void clearCache() {
        cache.clear();
    }

    public FirebaseToken get(String token) {
        CachedToken cachedToken = cache.get(token);
        if (cachedToken != null && cachedToken.isValid()) {
            return cachedToken.getToken();
        }
        return null;
    }

    public void put(String token, FirebaseToken firebaseToken) {
        cache.put(token, new CachedToken(firebaseToken, System.currentTimeMillis()));
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class CachedToken {
        private final FirebaseToken token;
        private final long timestamp;

        public boolean isValid() {
            return (System.currentTimeMillis() - timestamp) < TimeUnit.MINUTES.toMillis(50);
        }
    }
}
