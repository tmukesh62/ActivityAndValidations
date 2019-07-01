package activities;

public abstract class BaseActivityImpl<T extends Object, K extends Object> {
    BaseActivityImpl() {
    }

    public K execute(T request) {
        logRequest(request);

        validateRequest(request);

        Object o = doIdempotencyCheck(request);

        K response = null;
        if (o != null) {
            response = doExecuteIdempotent(request);
        } else {
            response = doExecute(request);
        }

        logResponse(response);

        return response;
    }

    // validate the request here
    protected abstract void validateRequest(T request);

    protected K doIdempotencyCheck(T request) {
        // this is optional
        return null;
    }

    // put the business logic here
    protected abstract K doExecute(T request);

    protected K doExecuteIdempotent(T request) {
        // this is optional
        return null;
    }

    private void logRequest(T request) {
        // log request here
    }

    private void logResponse(K response) {
        // log response here
    }
}
