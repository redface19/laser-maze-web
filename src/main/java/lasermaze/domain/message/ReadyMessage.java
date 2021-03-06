package lasermaze.domain.message;

import lasermaze.domain.GameRoom;
import lasermaze.domain.User;
import org.springframework.web.socket.WebSocketSession;

public class ReadyMessage implements Message {
    @Override
    public void process(GameRoom gameRoom, User user, WebSocketSession session) {
        gameRoom.getPlayer(user).pushReady();
        gameRoom.sendPlayerList();
        if (gameRoom.isAllReady()) {
            gameRoom.start();
        }
    }
}
