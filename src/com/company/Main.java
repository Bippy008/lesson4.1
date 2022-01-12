package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType;

    // 1 - warrior, 2 - mage, 3 - kinetic
    public static int[] heroesHealth = {270, 260, 250};
    public static int[] heroesDamage = {15, 20, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic"};

    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while(!isGameFinished()) {
            round();
        }
        System.out.println(isGameFinished());
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if(heroesAttackType[i] == bossDefenceType) {
                    Random random = new Random();
                    int coeff = random.nextInt(7) + 1; // 0, 1, 2, 3..., 7
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth -= heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth -= heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHits();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefenceType);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won.");
            return true;
        }
        /* if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won.");
            return true;     НЕ ПРАКТИЧНЫЙ ВАРИАНТ
        }
        return false; */
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won.");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber);
        System.out.println("Boss Health: " + bossHealth + " [" + bossDamage + "]");
        System.out.println("__________________________");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " Health: "  + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        System.out.println("__________________________");
    }

    // генерирует число от 1 до 20
    // от него должны зависеть урон босса, героев, лечение
    public static int diceRoll() {
        int result = (int)(Math.random() * 20) + 1;
        return result;
    }
}
