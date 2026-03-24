package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.game.TileType;

import java.sql.*;
import java.util.List;

public class GameStateServiceJDBC implements GameStateService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";

    public static final String UPSERT = "INSERT INTO game_state (game_name, player1, player2, currentplayer, score1, score2, board_data) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT (game_name) DO UPDATE SET " +
            "currentplayer = EXCLUDED.currentplayer, score1 = EXCLUDED.score1, " +
            "score2 = EXCLUDED.score2, board_data = EXCLUDED.board_data";

    public static final String SELECT = "SELECT * FROM game_state WHERE game_name = ?";

    @Override
    public void save(GameState state) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(UPSERT)) {

            statement.setString(1, state.getGameName());
            statement.setString(2, state.getPlayer1());
            statement.setString(3, state.getPlayer2());
            statement.setString(4, state.getCurrentPlayer());
            statement.setInt(5, state.getScore1());
            statement.setInt(6, state.getScore2());
            statement.setString(7, state.getBoard_data());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStateException("Error saving game state", e);
        }
    }

    @Override
    public GameState load(String gameName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setString(1, gameName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new GameState(
                            rs.getString("game_name"),
                            rs.getString("player1"),
                            rs.getString("player2"),
                            rs.getString("currentplayer"),
                            rs.getInt("score1"),
                            rs.getInt("score2"),
                            rs.getString("board_data")
                    );
                }
            }
        } catch (SQLException e) {
            throw new GameStateException("Error loading game state", e);
        }
        return null;
    }
}
