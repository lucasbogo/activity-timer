package com.example.activitytimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Criado por Lucas Bogo 06/09/2022
 * <p>
 * Classe Banco de Dados Básico da Aplicação ActivityTimer
 * <p>
 * Esta classe é privada, pois somente a {@link AppProvider} deve acessa-la; Trata-se de uma classe 'singleton'
 * <p>
 * Na programação orientada a objetos, uma classe singleton é uma classe que pode ter apenas um objeto
 * (uma instância da classe) por vez. Após a primeira vez, se tentarmos instanciar a classe Singleton,
 * a nova variável também aponta para a primeira instância criada. Portanto, qualquer modificação que
 * fizermos em qualquer variável dentro da classe por meio de qualquer instância, afetará a variável da
 * instância única criada e será visível se acessarmos essa variável por meio de qualquer variável desse
 * tipo de classe definido.
 * <p>
 * O objetivo principal de uma classe Singleton é restringir o limite do número de criação de objetos a apenas um.
 * Isso geralmente garante que haja controle de acesso a recursos, por exemplo, conexão de soquete ou banco de dados
 * <p>
 * <p>
 * <p>
 * Para criar uma classe singleton, devemos seguir os passos abaixo:
 * <p>
 * 1. Certifique-se de que exista apenas uma instância da classe.
 * <p>
 * 2. Forneça acesso global a essa instância por:
 * <p>
 * 2.1 Declarando todos os construtores da classe como privados.
 * <p>
 * 2.2 Fornecendo um método estático que retorna uma referência à instância.
 * O conceito de inicialização lenta é usado para escrever os métodos estáticos.
 * <p>
 * 2.3 A instância é armazenada como uma variável estática privada.
 */


class AppDatabase extends SQLiteOpenHelper {


    private static final String TAG = "AppDatabase";
    public static final String DATABASE_NAME = "activitytimer.db";
    public static final int DATABASE_VERSION = 1;
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: construtor bd");
    }

    /**
     * 'Pegar' uma instância do bd 'singleton', 'helper object do aplicativo
     *
     * @param context = o contexto do provedor de conteúdo | the content providers context
     * @return =  retorna um sqlite helper object | returns a sqlite helper object
     */
    static AppDatabase getInstance(Context context) {
        // Se a instância for nula
        if (instance == null) {
            Log.d(TAG, "getInstance: criando uma instância nova");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: começa criação bd");
        String sSQL; // Usa uma variável da String para facilitar logging

        sSQL = "CREATE TABLE " + ActivitiesContract.TABLE_NAME + " ("
                + ActivitiesContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ActivitiesContract.Columns.ACTIVITIES_NAME + " TEXT NOT NULL, "
                + ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION + " TEXT, "
                +ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER + " INTEGER);";

        Log.d(TAG, "onCreate: sSQL");
        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                // Atualizar primeira versão
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
}
