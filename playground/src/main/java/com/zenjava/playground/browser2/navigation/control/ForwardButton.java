package com.zenjava.playground.browser2.navigation.control;

import com.zenjava.playground.browser2.navigation.NavigationManager;
import javafx.scene.Node;

// todo use Control + skin
public class ForwardButton extends NavigationButton
{
    public ForwardButton()
    {
        this(null, null, null);
    }

    public ForwardButton(NavigationManager navigationManager)
    {
        this(null, null, navigationManager);
    }

    public ForwardButton(String label)
    {
        this(label, null, null);
    }

    public ForwardButton(String label, NavigationManager navigationManager)
    {
        this(label, null, navigationManager);
    }

    public ForwardButton(Node graphic)
    {
        this(null, graphic, null);
    }

    public ForwardButton(Node graphic, NavigationManager navigationManager)
    {
        this(null, graphic, navigationManager);
    }

    public ForwardButton(String label, Node graphic)
    {
        this(label, graphic, null);
    }

    public ForwardButton(String label, Node graphic, final NavigationManager navigationManager)
    {
        super(label, graphic, navigationManager);
    }

    protected void navigationManagerUpdated(NavigationManager oldNavigationManager, NavigationManager newNavigationManager)
    {
        disableProperty().unbind();
        if (newNavigationManager != null)
        {
            disableProperty().bind(new ListSizeBinding(newNavigationManager.getForwardHistory(), 0));
        }
    }

    protected void doAction()
    {
        getNavigationManager().goForward();
    }
}
