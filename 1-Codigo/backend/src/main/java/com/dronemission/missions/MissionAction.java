// ============================================================================================
// DOCUMENTACIÓN · missions/MissionAction.java
//
// QUÉ ES:        La lista de acciones (botones) posibles sobre una misión.
// QUÉ HACE:      EDIT, ASSIGN, SUBMIT, PREPARE, START (despegar), PAUSE, RESUME,
//                FINISH, CANCEL... Viajan al frontend en availableActions.
// SI LO CAMBIAS: Acción nueva: valor aquí + regla en MissionAccessService + botón en
//                MissionDetailPage.tsx.
// ============================================================================================
package com.dronemission.missions;

/** Acciones que un usuario puede ejecutar sobre una misión (se envían al frontend). */
public enum MissionAction {
    EDIT, DELETE, SUBMIT, ASSIGN, CANCEL, PREPARE, START, PAUSE, RESUME, FINISH, OBSERVE, INCIDENT, EVIDENCE, REPORT
}
