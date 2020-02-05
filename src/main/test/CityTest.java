package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityTest {

    @Test
    public void test_cout_construction_quartiers(){
        Board board = new Board();
        City city = new City(board);
        Possession possession = new Possession(0,null);
        city.buildDistrict(Card.MANOR_5);
        city.buildDistrict(Card.WATCHTOWER_2);
        city.buildDistrict(Card.TAVERN_5);
        int score = city.score(possession);
        //assert(score == 5);
        assertThat(score).isEqualTo(5);
    }

    @Test
    public void test_city_comprend_cinq_types_quartiers(){
        Board board = new Board();
        City city = new City(board);
        Possession possession = new Possession(0,null);
        city.buildDistrict(Card.MANOR_5);
        city.buildDistrict(Card.WATCHTOWER_2);
        city.buildDistrict(Card.TAVERN_5);
        city.buildDistrict(Card.TEMPLE_1);
        city.buildDistrict(Card.HAUNTED_CITY);

        int score = city.score(possession);
        //assert(score == 5);
        assertThat(score).isEqualTo(11);
    }

    @Test
    public void test_premier_conmplete_city(){
        Board board = new Board();
        City city = new City(board);
        Possession possession = new Possession(0,null);
        city.buildDistrict(Card.MANOR_5); //3
        city.buildDistrict(Card.WATCHTOWER_2); //1
        city.buildDistrict(Card.TAVERN_5); //1
        city.buildDistrict(Card.TEMPLE_1); //1
        city.buildDistrict(Card.CHURCH_2); //2
        city.buildDistrict(Card.CASTLE_2); //4
        city.buildDistrict(Card.PRISON_2); //2
        int score = city.score(possession);
        if (board.isFirst(city)) {
            assertThat(score).isEqualTo(18); //14 + 4 = 18
        }

    }

    @Test
    public void test_pas_premier_conmplete_city(){
        Board board = new Board();
        City city = new City(board);
        Possession possession = new Possession(0,null);
        city.buildDistrict(Card.MANOR_5); //3
        city.buildDistrict(Card.WATCHTOWER_2); //1
        city.buildDistrict(Card.TAVERN_5); //1
        city.buildDistrict(Card.TEMPLE_1); //1
        city.buildDistrict(Card.CHURCH_2); //2
        city.buildDistrict(Card.CASTLE_2); //4
        city.buildDistrict(Card.PRISON_2); //2
        int score = city.score(possession);
        if (city.isComplete() && !board.isFirst(city)) {
            assertThat(score).isEqualTo(16); //14 + 2 = 16
        }
    }

    @Test
    public void test_score_bonus_merveille(){
        Board board = new Board();
        City city = new City(board);
        Player player = new Player("Antoine", 21, city, new HumanController());
        CardPile pioche = new CardPile(Card.all().toList().shuffle());
        player.add(2);
        player.add(pioche.draw(2));
        city.buildDistrict(Card.DRAGON_GATE);
        city.buildDistrict(Card.UNIVERSITY);
        city.buildDistrict(Card.TREASURY);
        city.buildDistrict(Card.MAP_ROOM);
        int score = player.score();
        assertThat(score).isEqualTo(30);
    }
}
