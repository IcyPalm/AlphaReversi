package alphareversi.commands;

import alphareversi.commands.receive.RecvDisconnectCommand;
import alphareversi.commands.receive.RecvGameChallengeCanceledCommand;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameResultCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.receive.RecvHelpCommand;
import alphareversi.commands.receive.RecvMessageCommand;
import alphareversi.commands.receive.RecvPlayerlistCommand;
import alphareversi.commands.receive.RecvStatusErrCommand;
import alphareversi.commands.receive.RecvStatusOkCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timmein on 24/03/16.
 */
public class CommandParser {
    private static final Map<String, Class> CommandIdentifiers = new HashMap<String, Class>() {
        {
            put("SVR GAME CHALLENGE CANCELLED \\{(.*?)\\}", RecvGameChallengeCanceledCommand.class);
            put("SVR GAME CHALLENGE \\{(.*?)\\}", RecvGameChallengeCommand.class);
            put("SVR GAME MATCH \\{(.*?)\\}", RecvGameMatchCommand.class);
            put("SVR GAMELIST \\[(.*?)\\]", RecvGamelistCommand.class);
            put("SVR GAME MOVE \\{(.*?)\\}", RecvGameMoveCommand.class);
            put("SVR GAME (WIN|DRAW|LOSS) \\{(.*?)\\}", RecvGameResultCommand.class);
            put("SVR GAME YOURTURN \\{(.*?)\\}", RecvGameYourturnCommand.class);
            put("SVR PLAYERLIST \\[(.*?)\\]", RecvPlayerlistCommand.class);
            put("SVR MESSAGE \\{(.*?)\\}", RecvMessageCommand.class);
            put("SVR HELP (.*?)+", RecvHelpCommand.class);
            put("ERR (.*?)+", RecvStatusErrCommand.class);
            put("OK", RecvStatusOkCommand.class);
            put("DISCONNECT", RecvDisconnectCommand.class);
        }
    };

    /**
     * Parse a string to create a serverCommand object.
     * @param serverCommand string to be converted
     * @return RecvCommand
     */
    public static RecvCommand parseString(String serverCommand) {
        for (Map.Entry<String, Class> entry : CommandIdentifiers.entrySet()) {
            String regex = entry.getKey();
            Class commandClass = entry.getValue();
            if (serverCommand.matches(regex)) {
                try {
                    Constructor constructor = commandClass.getConstructor(String.class);
                    return (RecvCommand) constructor.newInstance(serverCommand);
                } catch (InstantiationException exception) {
                    exception.printStackTrace();
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                } catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                } catch (InvocationTargetException exception) {
                    exception.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        System.out.println("Unknown command received: " + serverCommand);
        return null;
    }

    /**
     * Removes all whitespacing from strings in array.
     * @param stringArray array to be converted
     * @return array of strings without whitespacing
     */
    public static String[] trimStringArray(String[] stringArray) {
        String[] trimmedArray = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            trimmedArray[i] = stringArray[i].trim();
        }
        return trimmedArray;
    }

    /**
     * explode a string to create a ArrayList.
     * @param string to be converted
     * @return listh with strings
     */
    public static ArrayList<String> parseArraylist(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!string.contains("[]")) {
            string = string.substring(1, string.length() - 1);
            String[] parts = CommandParser.trimStringArray(
                    string.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
            for (String part : parts) {
                arrayList.add(part.substring(1, part.length() - 1));
            }
        }
        return arrayList;
    }

    /**
     * explode a string to create a HashMap.
     * @param string to be converted
     * @return hashmap with data from string
     */
    public static HashMap parseObjectMap(String string) {
        HashMap<String, String> hashMap = new HashMap<>();
        string = string.substring(1, string.length() - 1);
        String[] parts = CommandParser.trimStringArray(string.split(","));
        for (String part : parts) {
            String[] trimStringArray = CommandParser.trimStringArray(part.split(":"));
            hashMap.put(
                        trimStringArray[0],
                        trimStringArray[1].substring(1, trimStringArray[1].length() - 1)
            );
        }
        return hashMap;
    }
}
