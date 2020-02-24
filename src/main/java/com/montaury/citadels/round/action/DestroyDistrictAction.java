package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.City;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import java.util.Iterator;


public class DestroyDistrictAction {

    public static Map<Player, List<DestructibleDistrict>> districtsDestructibleBy(GameRoundAssociations associations, Player player) {
        Map<Player, List<DestructibleDistrict>> destructibles = HashMap.empty();
        for (Group group : associations.associations) {
            if (group.isNot(Character.BISHOP) || group.isMurdered()) {
                destructibles = destructibles.put(group.player(), group.player().city().districtsDestructibleBy(player));
            }
        }
        return destructibles;
    }
    public static void destroyDistrict(Group groupe1,Group groupe2, Card card, CardPile pioche) {
        int valeur = card.district().cost() - 1;
        if (groupe2.player().city().has(District.GREAT_WALL)){
            valeur += 1 ;
        }
        groupe2.player().city().destroyDistrict(card);
        groupe1.player().pay(valeur);
        pioche.discard(card);
        System.out.println("BadaBoom ! " + groupe1.player().name() + " est passé par là, le " + card + " de " + groupe2.player().name() + " s'effondre dans un grand fracas !!");
    }


}
