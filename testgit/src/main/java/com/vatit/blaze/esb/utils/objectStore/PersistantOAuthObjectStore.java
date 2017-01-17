package com.vatit.blaze.esb.utils.objectStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.store.ObjectDoesNotExistException;
import org.mule.api.store.ObjectStoreException;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.FileUtils;
import org.mule.util.store.InMemoryObjectStore;
import org.mule.modules.oauth2.provider.token.AccessTokenStoreHolder;

public class PersistantOAuthObjectStore extends InMemoryObjectStore<AccessTokenStoreHolder> {

    protected File fileStore;
    protected String directory;
    private Map<String, AccessTokenStoreHolder> tokenStore;
    
    public PersistantOAuthObjectStore() {
    	super();
    }
    
    private Map<String, AccessTokenStoreHolder> getTokenStore() {
    	if (tokenStore == null)
    		tokenStore = new HashMap<>();
    	return tokenStore;
    }
    
    @Override
    public void initialise() throws InitialisationException
    {
        super.initialise();
        
        if (tokenStore == null || tokenStore.size() == 0) {
            if (directory == null)
            	directory = context.getConfiguration().getWorkingDirectory() + "/objectstore";
            try
            {
                File dir = FileUtils.openDirectory(directory);
                fileStore = new File(dir, name + ".dat");
                if (fileStore.exists())
                    loadFromStore();
            }
            catch (Exception e)
            {
                throw new InitialisationException(e, this);
            }
        }
    }

    @SuppressWarnings("unchecked")
	protected synchronized void loadFromStore() throws Exception
    {
    	ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileStore));
    	Object result = stream.readObject();
    	tokenStore = (Map<String, AccessTokenStoreHolder>)result;
        for (Map.Entry<String, AccessTokenStoreHolder> entry : getTokenStore().entrySet())
            super.store(entry.getKey().toString(), entry.getValue());

    	stream.close();
    }

    @Override
    public void store(Serializable id, AccessTokenStoreHolder item) throws ObjectStoreException
    {
        super.store(id, item);

        try
        {
        		
        	synchronized(getTokenStore()) {
        		getTokenStore().put(id.toString(), item);
            	saveMap();
        	}
        		
        }
        catch (IOException e)
        {
            throw new ObjectStoreException(e);
        }
    }

    private void saveMap() throws IOException {
      	FileOutputStream output = new FileOutputStream(fileStore, false);
        
        ObjectOutputStream stream = new ObjectOutputStream(output); 
        stream.writeObject(getTokenStore());
        stream.close();
    }
    
    @Override
    public AccessTokenStoreHolder remove(Serializable key) throws ObjectStoreException
    {
    	super.retrieve(key);
    	
        try
        {
        	synchronized (getTokenStore())
        	{
        		if (getTokenStore().containsKey(key)) {
        			AccessTokenStoreHolder val = getTokenStore().get(key);
        			getTokenStore().remove(key);
        			saveMap();
        			return val;
        		}
        	}
 
        	throw new ObjectDoesNotExistException(CoreMessages.objectNotFound(key));
        }
        catch (IOException e)
        {
            throw new ObjectStoreException(e);
        }
    }   
    
    @Override
    public void clear() throws ObjectStoreException
    {
    	super.clear();
    	
        try
        {
        	synchronized (getTokenStore()) {
        		getTokenStore().clear();
        		saveMap();
        	}
        }
        catch (IOException e)
        {
            throw new ObjectStoreException(e);
        }
    }

    public String getDirectory()
    {
        return directory;
    }

    public void setDirectory(String directory)
    {
        this.directory = directory;
    }

	@Override
	public boolean isPersistent() {
		return true;
	}

}
