package com.montaury.citadels;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrictAction;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DestroyDistrictTest {
    //city2.districtsDestructibleBy(player).get(0).card() récupére la première carte destructible !!!
    private Board board ;
    private City city1 ;
    private City city2;
    private Player player1;
    private Player player2;
    private CardPile pioche ;
    private Group groupe1;
    private Group groupe2;

    @Before
    public void setUp(){
        board = new Board();
        city1 = new City(board);
        city2 = new City(board);
        player1 = new Player("Abdul",22, city1, new HumanController());
        player2 = new Player("Donald",22, city2, new HumanController());
        groupe1 = new Group(player1, Character.WARLORD);
        groupe2 = new Group(player2, Character.KING);
        pioche = new CardPile(List.empty());
    }
    @Test
    public void no_district_destroyable(){
        city2.buildDistrict(Card.MANOR_5);
        city2.buildDistrict(Card.KEEP_1);
        assertThat(city2.districtsDestructibleBy(player1)).isEqualTo(List.empty());
    }

    @Test
    public void there_is_destroyable_district(){
        player1.add(90);
        city2.buildDistrict(Card.MANOR_5);
        city2.buildDistrict(Card.KEEP_1);
        assertThat(city2.districtsDestructibleBy(player1).get(0).card()).isEqualTo(Card.MANOR_5);
    }
    @Test
    public void player_destroys_district(){
        city2.buildDistrict(Card.MANOR_5);
        DestroyDistrictAction.destroyDistrict(groupe1, groupe2, Card.MANOR_5, pioche);
        assertThat(city2.districts()).isEqualTo(List.empty());
    }

    @Test
    public void player_should_loose_money(){
        player1.add(90);
        city2.buildDistrict(Card.MANOR_5);
        DestroyDistrictAction.destroyDistrict(groupe1, groupe2, Card.MANOR_5, pioche);
        assertThat(player1.gold()).isEqualTo(88);
    }

    @Test
    public void card_should_return_to_the_stack_bottom(){
        player1.add(90);
        city2.buildDistrict(Card.MANOR_5);
        DestroyDistrictAction.destroyDistrict(groupe1, groupe2, Card.MANOR_5, pioche);
        assertThat(pioche.draw(1).contains(Card.MANOR_5)).isEqualTo(true);
    }

    @Test
    public void card_should_cost_1_more_gold(){
        player1.add(90);
        city2.buildDistrict(Card.GREAT_WALL);
        city2.buildDistrict(Card.MANOR_5);
        DestroyDistrictAction.destroyDistrict(groupe1, groupe2, Card.MANOR_5, pioche);
        assertThat(player1.gold()).isEqualTo(87);
    }

    //Le append marche pas ! on comprend pas.
    @Test
    public void test(){
        player1.add(90);
        List<Group> association = List.empty();
        city2.buildDistrict(Card.MANOR_5);
        city2.buildDistrict(Card.WATCHTOWER_1);
        association.append(groupe1);
        association.append(groupe2);
        System.out.println(association.length());
        GameRoundAssociations groups = new GameRoundAssociations(association);
        DestroyDistrictAction.showDestructibleDistrict(groups,player1);
    }

}
