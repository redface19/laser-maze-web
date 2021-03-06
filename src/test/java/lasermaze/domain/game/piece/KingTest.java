package lasermaze.domain.game.piece;

import lasermaze.domain.game.LaserPointer;
import lasermaze.domain.game.piece.common.Direction;
import lasermaze.domain.game.piece.common.Point;
import lasermaze.domain.game.piece.common.Rotation;
import lasermaze.domain.game.user.UserDelimiter;
import lasermaze.support.fixture.LaserPointerFixture;
import lasermaze.support.fixture.PieceFixture;
import org.junit.Test;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class KingTest {
    private static final Logger log = getLogger(KingTest.class);

    @Test
    public void move() {
        King king = PieceFixture.createKing(UserDelimiter.BLACK, Direction.EAST, new Point(4, 1));
        king.move(Direction.SOUTH);
        assertThat(king.direction).isEqualTo(Direction.EAST);
        assertThat(king.point).isEqualTo(new Point(5, 1));
    }

    @Test
    public void rotate() {
        King king = PieceFixture.createKing(UserDelimiter.BLACK, Direction.EAST, new Point(4, 1));
        king.rotate(Rotation.COUNTERCLOCKWISE);
        assertThat(king.direction).isEqualTo(Direction.NORTH);
        assertThat(king.point).isEqualTo(new Point(4, 1));
    }

    @Test
    public void rotate2() {
        King king = PieceFixture.createKing(UserDelimiter.BLACK, Direction.EAST, new Point(7, 7));
        king.rotate(Rotation.CLOCKWISE);
        assertThat(king.direction).isEqualTo(Direction.SOUTH);
        assertThat(king.point).isEqualTo(new Point(7, 7));
    }

    @Test
    public void hit() {
        King king = PieceFixture.createKing(UserDelimiter.BLACK, Direction.EAST, new Point(7, 0));
        LaserPointer laserPointer = LaserPointerFixture.LASER_POINTER1;
        king.hit(laserPointer);
        assertThat(laserPointer.isEnd()).isTrue();
    }
}