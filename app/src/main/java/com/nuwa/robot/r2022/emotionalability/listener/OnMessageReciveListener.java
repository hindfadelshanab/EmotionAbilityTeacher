package com.nuwa.robot.r2022.emotionalability.listener;

public interface OnMessageReciveListener {

      void  OnMessageRecive(String Message);
}
//initLevelSpinner(unitIndex+1, 0);

/*

private void initEndedGame(int endedUnit , int endedLevel) {
        if (endedUnit>0){

            List<Unit> units = realm.where(Unit.class).findAll();

            if (units != null) {

                if (units.size() > endedUnit) {
                    initUnitSpinner(1, endedUnit);

                    levels = realm.where(Level.class).equalTo("unitId" , endedUnit).findAll();
                    if (endedLevel>0) {
                        int endedLevelPotion = Constants.LEVEL_ID_AND_POSITION_HASH_MAP.get(endedLevel);
                    }

                } else if (units.size() == endedUnit ) {
                    initUnitSpinner(1, 0);
//                    levels = realm.where(Level.class).equalTo("unitId" , 0).findAll();


                }
            }


        }else {
            initUnitSpinner(1, 0);

        }
    }
 */