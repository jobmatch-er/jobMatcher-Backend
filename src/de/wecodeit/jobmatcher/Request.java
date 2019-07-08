package de.wecodeit.jobmatcher;

import java.util.List;

public abstract class Request
{
    public abstract String getRequest();

    public abstract String respond(List<String> args);
}
