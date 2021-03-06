package lasermaze.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GameRoomRepository {
    private Map<String, GameRoom> gameRooms = new HashMap<>();

    public void save(GameRoom gameRoom) {
        gameRooms.put(gameRoom.getId(), gameRoom);
    }

    public GameRoom getGameRoom(String id) {
        return gameRooms.get(id);
    }

    public void removeSessionUser(WebSocketSession session) {
        for (String key : gameRooms.keySet()) {
            gameRooms.get(key).remove(session);
            if(gameRooms.get(key).isEmpty()) gameRooms.remove(key);
        }
    }

    public List<GameRoom> getAllRooms() {
        return gameRooms.values().parallelStream().collect(Collectors.toList());
    }
}
