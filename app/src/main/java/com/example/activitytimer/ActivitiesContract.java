package com.example.activitytimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.activitytimer.AppProvider.CONTENT_AUTHORITY;
import static com.example.activitytimer.AppProvider.CONTENT_AUTHORITY_URI;


/**
 * CONTRACT INTERFACE FOR CONSTANT VALUES
 * INTERFACE CHAMADA 'CONTRACT' QUE SERVE P/ DECLARAR VALORES CONSTANTES
 *
 * OU SEJA, ESSA CLASSE SERVE PARA DECLARAR AS CONSTANTES (NOME_COLUNAS)
 * QUE SERÃO UTILIZADAS NESTE PROJETO. ASSIM NÃO SERÁ NECESSÁRIO CRIAR INSTÂNCIAS DA CLASSE COLUNA
 *
 */

public class ActivitiesContract {

    static final String TABLE_NAME = "activities";

    // Activities fields
    public static class Columns {

        public static final String _ID = BaseColumns._ID;
        public static final String ACTIVITIES_NAME = "name";
        public static final String ACTIVITIES_DESCRIPTION = "description";
        public static final String ACTIVITIES_SORT_ORDER = "sort_order";

        // Construtor privado para prevenir instanciação
        private Columns() {
            /**
             * Esse construtor não tem nenhum funcionalidade. Porém, como está privado
             * instâncias da classe coluna não poderão ser criadas...
             */
        }
    }

    /**
     * Uri que acessa a 'activities' table.
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    // Constantes:
    static final String CONTENT_TYPE_= "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE_= "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildActivityUri(long activitiesId) {
        return ContentUris.withAppendedId(CONTENT_URI, activitiesId);
    }

    static long getActivityId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
