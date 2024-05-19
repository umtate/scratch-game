package za.scratch.game.rewards.base;

import za.scratch.game.common.models.config.Symbols;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.rewards.models.RewardsHolder;
import za.scratch.game.rewards.models.Winnings;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignRewardBaseClass {

    private static final String BONUS_TYPE = "bonus";

    private Map<String, Integer> countSymbols(List<List<String>> listOfLists) {
        Map<String, Integer> stringCounts = new HashMap<>();
        for (List<String> innerList : listOfLists) {
            for (String str : innerList) {
                stringCounts.put(str, stringCounts.getOrDefault(str, 0) + 1);
            }
        }
        return stringCounts;
    }

    private String filterForBonusSymbol(Map<String, Symbols> map, List<String> matrixSymbols) {
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


    private double calculateRewards(RewardsHolder rewardsHolder){
        var listOfSymbolWinnings = new ArrayList<Double>();

        for (Map.Entry<String, List<String>> combination : rewardsHolder.winnings().appliedWinningCombinations().entrySet()) {
            if(rewardsHolder.winnings().winningValue().containsKey(combination.getKey())){
                var applied = rewardsHolder.winnings().winningValue().get(combination.getKey())*rewardsHolder.betAmount();
                var symbolRewards =  rewardsHolder.winnings().symbolWinnings().get(combination.getValue().get(0))*applied;
                listOfSymbolWinnings.add(symbolRewards);
            }
        }

        return listOfSymbolWinnings
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double factorBonus(String appliedBonusSymbol, double rewards) {
        Pattern numberPattern = Pattern.compile("-?\\d+");

        Pattern operatorPattern = Pattern.compile("[^\\d]+");

        Matcher number = numberPattern.matcher(appliedBonusSymbol);
        Matcher operator = operatorPattern.matcher(appliedBonusSymbol);

        if(operator.find() && number.find()){
            rewards = operator.group().equals("*") ?
                    rewards * Double.parseDouble(number.group()):
                    rewards + Double.parseDouble(number.group());
        }
        return rewards;
    }

    protected String getBonusSymbol(RewardsRequest rewardsRequest) {
        var flatMatrixList = rewardsRequest.matrix().value()
                .stream().flatMap(List::stream)
                .toList();

        return filterForBonusSymbol(rewardsRequest.symbols(), flatMatrixList);
    }

    protected Winnings getWinnings(RewardsRequest rewardsRequest) {
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        Map<String, Double> winningValue = new HashMap<>();
        Map<String, Double> symbolValues = new HashMap<>();

        countSymbols(rewardsRequest.matrix().value()).forEach((symbol, count) -> {
            var listForRewards = new ArrayList<String>();

            rewardsRequest.winCombinations().forEach((s, wc) -> {
                if(Objects.equals(wc.getCount(), count)){
                    for (Map.Entry<String, Symbols> entry : rewardsRequest.symbols().entrySet()) {
                        if (entry.getKey().equals(symbol)) {
                            winningValue.put(symbol, entry.getValue().getReward_multiplier());
                        }
                    }
                    symbolValues.put(s, wc.getReward_multiplier());
                    listForRewards.add(s);
                }
            });

            if(!listForRewards.isEmpty()){
                appliedWinningCombinations.put(symbol, listForRewards);
            }
        });

        return Winnings.builder()
                .winningValue(winningValue)
                .appliedWinningCombinations(appliedWinningCombinations)
                .symbolWinnings(symbolValues)
                .build();
    }

    protected double getRewards(RewardsRequest rewardsRequest, Winnings winnings, String appliedBonusSymbol) {
        var rewardsHolder = RewardsHolder
                .builder()
                .winnings(winnings)
                .winCombinations(rewardsRequest.winCombinations())
                .betAmount(rewardsRequest.betAmount())
                .build();


        var rewards = calculateRewards(rewardsHolder);

        rewards = factorBonus(appliedBonusSymbol, rewards);
        return rewards;
    }

}
