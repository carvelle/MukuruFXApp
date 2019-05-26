package za.co.makuru.makurufx.utils;

import com.google.gson.Gson;

import za.co.makuru.makurufx.data.network.gsonparserfactory.GsonParserFactory;
import za.co.makuru.makurufx.interfaces.Parser;

public class ParseUtil {

    private static Parser.Factory mParserFactory;

    public static void setParserFactory(Parser.Factory parserFactory) {
        mParserFactory = parserFactory;
    }

    public static Parser.Factory getParserFactory() {
        if (mParserFactory == null) {
            mParserFactory = new GsonParserFactory(new Gson());
        }
        return mParserFactory;
    }

    public static void shutDown() {
        mParserFactory = null;
    }

}