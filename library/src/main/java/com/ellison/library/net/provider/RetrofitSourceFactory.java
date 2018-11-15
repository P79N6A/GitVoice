package com.ellison.library.net.provider;

/**
 *
 * @author Ellison
 * @date 2017/10/19
 *
 * 数据工厂类
 */
@SuppressWarnings("unchecked")
public class RetrofitSourceFactory {

    private static IDataSource source;

    private RetrofitSourceFactory() {

    }

    /**
     * 获取数据源
     * @param clz {@link  RetrofitFakeSource}
     * @param <T>
     * @return
     */
    public static <T extends IDataSource> T createDataSource(Class<T> clz) {
        try {
            source = (IDataSource) Class.forName(clz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) source;
    }

}
