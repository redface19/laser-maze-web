package lasermaze.domain;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lasermaze.socket.MessageSendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class GameRoom {
    private static final Logger log = LoggerFactory.getLogger(GameRoom.class);


    public static final int MAX_PLAYER_COUNT = 2;
    private String id;
    private String name;
    private Set<Player> players = new HashSet<>();

    public static GameRoom create(String name) {
        GameRoom created = new GameRoom();
        created.id = UUID.randomUUID().toString();
        created.name = name;
        return created;
    }

    public void join(Player player) {
        players.add(player);
    }

    public void sendPlayerList(ObjectMapper objectMapper) {
        send(players, objectMapper);
    }

    public <T> void send(T messageObject, ObjectMapper objectMapper) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            players.parallelStream()
                    .forEach(player -> MessageSendUtils.sendMessage(player.getWebSocketSession(), message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void remove(WebSocketSession target, ObjectMapper objectMapper) {
        Optional<Player> removablePlayer = players.parallelStream()
                .filter(player -> player.hasSameSession(target))
                .findFirst();

        if (removablePlayer.isPresent()) {
            players.remove(removablePlayer.get());
            sendPlayerList(objectMapper);
        }
    }

    public Player getPlayer(User user) {
        return players.parallelStream().filter(player -> player.isSameUser(user)).findFirst().get();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFull() {
        return players.size() == MAX_PLAYER_COUNT;
    }

    public boolean isAllReady() {
        return players.parallelStream().filter(player -> player.isReady()).count() == MAX_PLAYER_COUNT;
    }
}