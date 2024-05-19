package za.scratch.game.rewards.base;

import za.scratch.game.common.models.config.Symbols;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.rewards.models.RewardsHolder;

import java.util.*;

class AssignRewardsUtils {

    private static final String BONUS_TYPE = "bonus";
    private static final String HORIZONTAL_WINNING_COMBINATION = "same_symbols_horizontally";
    private static final String VERTICAL_WINNING_COMBINATION = "same_symbols_vertically";
    private static final String IMPACT_MULTIPLY = "multiply_reward";

    private static Map<String, Integer> countSymbols(List<List<String>> listOfLists) {
        Map<String, Integer> stringCounts = new HashMap<>();
        for (List<String> innerList : listOfLists) {
            for (String str : innerList) {
                stringCounts.put(str, stringCounts.getOrDefault(str, 0) + 1);
            }
        }
        return stringCounts;
    }

    private static void addWinningFromAxis(List<String> list,
                                    Map<String, List<String>> appliedWinningCombinations,
                                    String horizontalWinningCombination,
                                    RewardsRequest rewardsRequest,
                                    Map<String, List<Double>> symbolValues) {

        var symbol = list.get(0);
        var winnings = appliedWinningCombinations.get(symbol);
        winnings.add(horizontalWinningCombination);

        var horizontal = rewardsRequest.winCombinations().get(horizontalWinningCombination);
        var rewardsList = symbolValues.get(symbol);
        rewardsList.add(horizontal.getReward_multiplier());
    }

    private static boolean allEqual(List<String> list) {
        return list.stream().allMatch(item -> item.equals(list.get(0)));
    }

    public static String filterForBonusSymbol(Map<String, Symbols> map, List<String> matrixSymbols) {
        List<String> filteredKeys = new ArrayList<>();

        for (Map.Entry<String, Symbols> entry : map.entrySet()) {
            if (entry.getValue().getType().equals(BONUS_TYPE)) {
                filteredKeys.add(entry.getKey());
            }
        }

        var filteredKeysStream = filteredKeys.stream()
                .filter(key -> matrixSymbols.stream().anyMatch(symbol -> symbol.equals(key)))
                .toList();

        return filteredKeysStream.isEmpty() ? "" : filteredKeysStream.get(0);
    }

    public static double calculateRewards(RewardsHolder rewardsHolder){
        var listOfSymbolWinnings = new ArrayList<Double>();
        var winning = rewardsHolder.winnings();

        for (Map.Entry<String, List<String>> combination : winning.appliedWinningCombinations().entrySet()) {
            var key = combination.getKey();
            if(winning.winningValue().containsKey(key)){
                var applied = winning.winningValue().get(key)*rewardsHolder.betAmount();
                var symbolWinnings = winning.symbolWinnings().get(key);
                var loopCount = 1;
                var totalRewards = 0.0;

                for (var reward : symbolWinnings) {
                    totalRewards = loopCount == 1 ? applied * reward :totalRewards * reward;
                    loopCount++;
                }

                listOfSymbolWinnings.add(totalRewards);
            }
        }

        return listOfSymbolWinnings
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static double factorBonus(String appliedBonusSymbol, double rewards, Map<String, Symbols> symbols) {

        if (symbols.get(appliedBonusSymbol) != null) {
            var bonus = symbols.get(appliedBonusSymbol);
            rewards = Objects.equals(bonus.getImpact(), IMPACT_MULTIPLY) ?
                    rewards * bonus.getReward_multiplier() :
                    rewards + bonus.getExtra();
        }

        return rewards;
    }

    public static void addNormalWinnings(RewardsRequest rewardsRequest,
                                   Map<String, Double> winningValue,
                                   Map<String, List<Double>> symbolValues,
                                   Map<String, List<String>> appliedWinningCombinations) {
        countSymbols(rewardsRequest.matrix().value()).forEach((symbol, count) -> {
            var listForRewards = new ArrayList<String>();
            var symbolRewardsValues = new ArrayList<Double>();

            rewardsRequest.winCombinations().forEach((s, wc) -> {
                if(Objects.equals(wc.getCount(), count)){
                    for (Map.Entry<String, Symbols> entry : rewardsRequest.symbols().entrySet()) {
                        if (entry.getKey().equals(symbol)) {
                            winningValue.put(symbol, entry.getValue().getReward_multiplier());
                        }
                    }
                    symbolRewardsValues.add(wc.getReward_multiplier());
                    symbolValues.put(symbol, symbolRewardsValues);
                    listForRewards.add(s);
                }
            });

            if(!listForRewards.isEmpty()){
                appliedWinningCombinations.put(symbol, listForRewards);
            }
        });
    }

    public static void addHorizontalWinnings(RewardsRequest rewardsRequest,
                                       Map<String, List<Double>> symbolValues,
                                       Map<String, List<String>> appliedWinningCombinations){

        rewardsRequest.matrix().value().stream()
                .filter(list -> list.stream().distinct().limit(2).count() <= 1)
                .forEach(list -> {
                    addWinningFromAxis(list, appliedWinningCombinations, HORIZONTAL_WINNING_COMBINATION, rewardsRequest, symbolValues);
                });
    }

    public static void addVerticalWinnings(RewardsRequest rewardsRequest,
                                     Map<String, List<Double>> symbolValues,
                                     Map<String, List<String>> appliedWinningCombinations) {
        var listOfLists = rewardsRequest.matrix().value();
        int listSize = listOfLists.get(0).size();
        for (int i = 0; i < listSize; i++) {
            List<String> valuesAtIndex = new ArrayList<>();
            int currentIndex = i;
            listOfLists.forEach(list -> valuesAtIndex.add(list.get(currentIndex)));

            if (allEqual(valuesAtIndex)) {
                addWinningFromAxis(valuesAtIndex, appliedWinningCombinations, VERTICAL_WINNING_COMBINATION, rewardsRequest, symbolValues);
            }
        }
    }

}
