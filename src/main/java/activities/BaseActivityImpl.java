package activities;

public abstract class BaseActivityImpl<T extends Object, K extends Object> {
    protected final T request;
    protected K response;
    BaseActivityImpl(T request) {
        this.request = request;
    }

    public K execute() {
        logRequest();

        validateRequest();

        Object o = doIdempotencyCheck();

        response = null;
        if (o != null) {
            response = doExecuteIdempotent();
        } else {
            response = doExecute();
        }

        logResponse();

        return response;
    }

    protected abstract void validateRequest();

    protected K doIdempotencyCheck() {
        // this is optional
        return null;
    }

    protected abstract K doExecute();

    protected K doExecuteIdempotent() {
        // this is optional
        return null;
    }

    private void logRequest() {
        // log request here
    }

    private void logResponse() {
        // log response here
    }
}
