package MomApiPackage;

/**
 * Created by Robin on 25/05/2016.
 */
public interface RequestCallback <T> {
    public void onSuccess(T arg);
    public void onError(MomErrors err);
}
