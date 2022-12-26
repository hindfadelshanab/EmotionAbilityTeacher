package com.nuwa.robot.r2022.emotionalability.utils;

import java.util.HashMap;

public class Constants {
    public final static int SERVER_PORT = 3003;
    public final static String MESSAGE_FOR_SHOW_IMAGE_KEY = "messageForShowImageKey";

    public final static String MESSAGE_FOR_FACE_EXPRESSION_KEY = "messageForFaceExpressionKey";
    public final static String MESSAGE_FOR_BODY_MOTION_KEY = "messageForBodyMotionKey";
    public final static String MESSAGE_FOR_SHOW_IMAGE = "messageForShowImage";
    public final static String MESSAGE_FOR_KEBHI_SPECK = "messageForKhabiSpeack";
    public final static String MESSAGE_FOR_Robot_SPEAK_FACIAL_EXPRESSION = "messageForRobotSpeckFacialExpression";

    public final static String ModuleNameKEY = "moduleName";
    public final static String LEVEL_ID_Key = "levelId";
    public final static String LEVEL_ENDED_ID_Key = "levelEndedId";
    public final static String MODULE_ENDED_ID_Key = "moduleEndedId";
    public final static String UNIT_ID_KEY = "unitId";
    public final static String MODULE_ID_KEY = "moduleId";
    public final static String UNIT_ENDED_ID_KEY = "unitEndedId";
    public final static String IPKEY = "ip";
    public final static String LET_IS_BEGIN_VALUE = "Let’s Begin";
    public final static String LET_IS_TRY_AGAIN = "Let’s try again";
    public final static String ENGLISH = "English";
    public final static String ARABIC = "Arabic";
    public final static String LANGUAGE = "lang";
    public final static String LANGUAGE_SIGN_IN = "language";
    public final static String DOCTOR_ID = "doctorId";
    public final static String DOCTOR_NAME = "doctorName";
    public final static String PATIENT_ID = "patientId";
    public final static String BASELINE_ID = "baseLineId";
    public final static String BASELINE_POSITION = "baseLinePosition";
    public final static String TERM_POSITION = "termPosition";
    public final static String PATIENT_POSITION = "patientPosition";
    public final static String TERM_ID = "termId";

    public final static String CURRICULUM_ID = "curriculumId";
    public final static String MODULE_ID = "module_Id";
    public final static String SPECIALIZATION_ID = "specializationId";
    public final static String OBJECTIVES_ID = "ObjectivesId";
    public final static String SELECTED_OBJECTIVES = "selectedObjective";


    public final static int ANSWERED_WAY_TRU_FALSE = 1;
    public final static int ANSWERED_WAY_SELECT = 2;
    public final static int ANSWERED_WAY_DRAG = 3;
    public final static int PHASE_ANSWERED_TRUE = 1;
    public final static int PHASE_ANSWERED_FALSE = 2;
    public final static int ANSWERED_DRAG_BY_LETTER = 4;
    public final static int ANSWERED_WAY_SELECT_FROM_TABLE = 5;
    public final static HashMap<Integer, Integer> LEVEL_ID_AND_POSITION_HASH_MAP = new HashMap<>();
    public final static HashMap<Integer, Integer> UNIT_ID_AND_POSITION_HASH_MAP = new HashMap<>();

    public final static String BASE_URL = "https://qlickhealth.com/admin/index.php/api/Education_program/";

    public static HashMap<Integer, Integer> CURRICULUM_MODULE_MAPPING = new HashMap<>();
    public static HashMap<String, Integer> MODULE_UNIT_MAPPING = new HashMap<>();
    public static HashMap<String, Integer> SPECIALIZATION_LEVEL_MAPPING = new HashMap<>();

    public static void addToHash() {
        CURRICULUM_MODULE_MAPPING.put(1, 1);

        MODULE_UNIT_MAPPING.put("1", 1);
        MODULE_UNIT_MAPPING.put("3", 2);
        MODULE_UNIT_MAPPING.put("5", 3);
        SPECIALIZATION_LEVEL_MAPPING.put("1", 1);
        SPECIALIZATION_LEVEL_MAPPING.put("5", 2);
        SPECIALIZATION_LEVEL_MAPPING.put("7", 3);
        SPECIALIZATION_LEVEL_MAPPING.put("9", 4);
        SPECIALIZATION_LEVEL_MAPPING.put("11", 5);
        SPECIALIZATION_LEVEL_MAPPING.put("13", 6);


    }
    public static void addToLEVEL_ID_AND_POSITION_HASH_MAP(){
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(1, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(2, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(3, 1);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(4, 2);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(5, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(6, 1);
//        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(7, 0);
//        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(8, 1);
    }

    public static void addToUNIT_ID_AND_POSITION_HASH_MAP(){
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(0 ,0);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(1 ,1);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(2 ,2);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(3 ,3);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(4 ,4);
    }


}
