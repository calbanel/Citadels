package com.montaury.citadels;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DestroyDistrictTest {
    //city2.districtsDestructibleBy(player).get(0).card() récupére la première carte destructible !!!
    private Board board ;
    private City city1 ;
    private City city2;
    private Player player;
    private CardPile pioche ;

    @Before
    public void setUp(){
        board = new Board();
        city1 = new City(board);
        city2 = new City(board);
        player = new Player("Abdul",22, city1, new HumanController());
        pioche = new CardPile(List.empty());
    }
    @Test
    public void no_district_destroyable(){
        city2.buildDistrict(Card.MANOR_5);
        city2.buildDistrict(Card.KEEP_1);
        assertThat(city2.districtsDestructibleBy(player)).isEqualTo(List.empty());
    }

    @Test
    public void there_is_destroyable_district(){
        player.add(90);
        city2.buildDistrict(Card.MANOR_5);
        city2.buildDistrict(Card.KEEP_1);
        assertThat(city2.districtsDestructibleBy(player).get(0).card()).isEqualTo(Card.MANOR_5);
    }
    @Test
    public void player_destroys_district(){
        city2.buildDistrict(Card.MANOR_5);
        player.destroyDistrict(city2, Card.MANOR_5, pioche);
        assertThat(city2.districts()).isEqualTo(List.empty());
    }

    @Test
    public void player_should_loose_money(){
        player.add(90);
        city2.buildDistrict(Card.MANOR_5);
        player.destroyDistrict(city2, Card.MANOR_5, pioche);
        assertThat(player.gold()).isEqualTo(88);
    }

    @Test
    public void card_should_return_to_the_stack_bottom(){
        player.add(90);
        city2.buildDistrict(Card.MANOR_5);
        player.destroyDistrict(city2, Card.MANOR_5, pioche);
        assertThat(pioche.draw(1).contains(Card.MANOR_5)).isEqualTo(true);
    }
    @Test
    public void player_is_the_warlord(){
        Group groupe = new Group(player, Character.WARLORD);
        assertThat(groupe.character()).isEqualTo(Character.WARLORD);
    }

}
