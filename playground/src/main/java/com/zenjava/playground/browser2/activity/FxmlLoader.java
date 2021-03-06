package com.zenjava.playground.browser2.activity;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

public class FxmlLoader
{
    @SuppressWarnings("unchecked")
    public <Type extends HasNode> Type load(String fxmlFile)
            throws FxmlLoadException
    {
        return (Type) load(fxmlFile, null, null);
    }

    @SuppressWarnings("unchecked")
    public <Type extends HasNode> Type load(String fxmlFile, String resourceBundle)
            throws FxmlLoadException
    {
        return (Type) load(fxmlFile, ResourceBundle.getBundle(resourceBundle), null);
    }

    @SuppressWarnings("unchecked")
    public <Type extends HasNode> Type load(String fxmlFile, ResourceBundle resources, Map<String, Object> variables)
            throws FxmlLoadException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(fxmlFile);
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());

            File file = new File(fxmlFile);
            loader.setLocation(file.toURI().toURL());

            if (resources != null)
            {
                loader.setResources(resources);
            }

            if (variables != null)
            {
                loader.getNamespace().putAll(variables);
            }

            Node view = (Node) loader.load(fxmlStream);

            Type controller = (Type) loader.getController();
            if (controller instanceof AbstractActivity)
            {
                ((AbstractActivity) controller).setNode(view);
            }
            return controller;
        }
        catch (Exception e)
        {
            // map checked exception to a runtime exception - this is a system failure, not a business logic failure
            // so using checked exceptions for this is not necessary.
            throw new FxmlLoadException(String.format(
                    "Unable to load FXML from '%s': %s", fxmlFile, e.getMessage()), e);
        }
        finally
        {
            if (fxmlStream != null)
            {
                try
                {
                    fxmlStream.close();
                }
                catch (IOException e)
                {
                    System.err.println("WARNING: error closing FXML stream: " + e);
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
