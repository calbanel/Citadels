package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityTest {
    private Player player ;
    private Board board ;
    private City city ;
    private CardPile pioche ;

    @Before
    public void setUp(){
        board = new Board();
        city = new City(board);
        player = new Player("Antoine", 21, city, new HumanController());
        player.add(99);
        pioche = new CardPile(Card.all().toList().shuffle());
        player.add(pioche.draw(2));
    }

    @Test
    public void test_cout_construction_quartiers(){
        player.buildDistrict(Card.MANOR_5);
        player.buildDistrict(Card.WATCHTOWER_2);
        player.buildDistrict(Card.TAVERN_5);
        int score = player.score();
        assertThat(score).isEqualTo(5);
    }

    @Test
    public void test_city_comprend_cinq_types_quartiers(){
        player.buildDistrict(Card.MANOR_5);
        player.buildDistrict(Card.WATCHTOWER_2);
        player.buildDistrict(Card.TAVERN_5);
        player.buildDistrict(Card.MAGIC_SCHOOL);
        player.buildDistrict(Card.HAUNTED_CITY);

        int score = player.score();
        assertThat(score).isEqualTo(16);
    }

    @Test
    public void test_premier_complete_city(){
        player.buildDistrict(Card.MANOR_5);
        player.buildDistrict(Card.WATCHTOWER_2);
        player.buildDistrict(Card.TAVERN_5);
        player.buildDistrict(Card.TEMPLE_1);
        player.buildDistrict(Card.CHURCH_2);
        player.buildDistrict(Card.CASTLE_2);
        player.buildDistrict(Card.PRISON_2);
        int score = player.score();
        assertThat(score).isEqualTo(18);
    }

    @Test
    public void test_pas_premier_complete_city(){
        City firstCity = new City(board);
        firstCity.buildDistrict(Card.TRADING_POST_1);
        firstCity.buildDistrict(Card.PRISON_1);
        firstCity.buildDistrict(Card.TEMPLE_2);
        firstCity.buildDistrict(Card.MAGIC_SCHOOL);
        firstCity.buildDistrict(Card.BATTLEFIELD_1);
        firstCity.buildDistrict(Card.WATCHTOWER_3);
        firstCity.buildDistrict(Card.WATCHTOWER_1);
        player.buildDistrict(Card.MANOR_5);
        player.buildDistrict(Card.WATCHTOWER_2);
        player.buildDistrict(Card.TAVERN_5);
        player.buildDistrict(Card.TEMPLE_1);
        player.buildDistrict(Card.CHURCH_2);
        player.buildDistrict(Card.CASTLE_2);
        player.buildDistrict(Card.PRISON_2);

        int score = player.score();
        assertThat(score).isEqualTo(16);
    }

    @Test
    public void test_score_bonus_DRAGON_GATE(){
        player.buildDistrict(Card.DRAGON_GATE);
        int score = player.score();
        assertThat(score).isEqualTo(8);
    }

    @Test
    public void test_score_bonus_UNIVERSITY(){
        player.buildDistrict(Card.UNIVERSITY);
        int score = player.score();
        assertThat(score).isEqualTo(8);
    }

    @Test
    public void test_score_bonus_TREASURY(){
        player.buildDistrict(Card.TREASURY);
        int score = player.score();
        assertThat(score).isEqualTo(5+player.gold());
    }

    @Test
    public void test_score_bonus_MAP_ROOM(){
        player.buildDistrict(Card.MAP_ROOM);
        int score = player.score();
        assertThat(score).isEqualTo(7);
    }

    @Test
    public void test_score_bonus_all_merveille(){
        player.buildDistrict(Card.DRAGON_GATE);
        player.buildDistrict(Card.UNIVERSITY);
        player.buildDistrict(Card.TREASURY);
        player.buildDistrict(Card.MAP_ROOM);
        int score = player.score();
        assertThat(score).isEqualTo(26+player.gold()+2);
    }
}
