import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceAuthority from './service-authority';
import ServiceAuthorityDetail from './service-authority-detail';
import ServiceAuthorityUpdate from './service-authority-update';
import ServiceAuthorityDeleteDialog from './service-authority-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceAuthorityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceAuthorityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceAuthorityDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceAuthority} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ServiceAuthorityDeleteDialog} />
  </>
);

export default Routes;
