
package com.liferay.portal.events;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;


public class RedirectLoginPostAction extends Action
{
    private static Log _log = LogFactoryUtil.getLog(RedirectLoginPostAction.class);

    @Override public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException
    {
        _log.info("Perform Redirect post Login");

		long userId = PortalUtil.getUserId(request);
        try
        {
            List<Organization> userOrganizations = OrganizationLocalServiceUtil.getUserOrganizations(userId);
            if (userOrganizations != null && userOrganizations.size() == 1)
            {
                Organization org = userOrganizations.get(0);
                String path = PortalUtil.getPathFriendlyURLPrivateGroup() + org.getGroup().getFriendlyURL();
                response.sendRedirect(path);
            }
        }
        catch (Exception e)
        {
            _log.warn("Unable to redirect to organization page, userId=" + userId, e);
        }
    }
}
