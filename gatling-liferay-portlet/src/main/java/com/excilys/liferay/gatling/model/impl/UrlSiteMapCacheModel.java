package com.excilys.liferay.gatling.model.impl;

import com.excilys.liferay.gatling.model.UrlSiteMap;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UrlSiteMap in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UrlSiteMap
 * @generated
 */
public class UrlSiteMapCacheModel implements CacheModel<UrlSiteMap>,
    Externalizable {
    public long urlSiteMapId;
    public long siteMapId;
    public String friendlyUrl;
    public String url;
    public int weight;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(11);

        sb.append("{urlSiteMapId=");
        sb.append(urlSiteMapId);
        sb.append(", siteMapId=");
        sb.append(siteMapId);
        sb.append(", friendlyUrl=");
        sb.append(friendlyUrl);
        sb.append(", url=");
        sb.append(url);
        sb.append(", weight=");
        sb.append(weight);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public UrlSiteMap toEntityModel() {
        UrlSiteMapImpl urlSiteMapImpl = new UrlSiteMapImpl();

        urlSiteMapImpl.setUrlSiteMapId(urlSiteMapId);
        urlSiteMapImpl.setSiteMapId(siteMapId);

        if (friendlyUrl == null) {
            urlSiteMapImpl.setFriendlyUrl(StringPool.BLANK);
        } else {
            urlSiteMapImpl.setFriendlyUrl(friendlyUrl);
        }

        if (url == null) {
            urlSiteMapImpl.setUrl(StringPool.BLANK);
        } else {
            urlSiteMapImpl.setUrl(url);
        }

        urlSiteMapImpl.setWeight(weight);

        urlSiteMapImpl.resetOriginalValues();

        return urlSiteMapImpl;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        urlSiteMapId = objectInput.readLong();
        siteMapId = objectInput.readLong();
        friendlyUrl = objectInput.readUTF();
        url = objectInput.readUTF();
        weight = objectInput.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput)
        throws IOException {
        objectOutput.writeLong(urlSiteMapId);
        objectOutput.writeLong(siteMapId);

        if (friendlyUrl == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(friendlyUrl);
        }

        if (url == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(url);
        }

        objectOutput.writeInt(weight);
    }
}