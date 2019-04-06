import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmergencyService from './emergency-service';
import EmergencyServiceDetail from './emergency-service-detail';
import EmergencyServiceUpdate from './emergency-service-update';
import EmergencyServiceDeleteDialog from './emergency-service-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmergencyServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmergencyServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmergencyServiceDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmergencyService} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmergencyServiceDeleteDialog} />
  </>
);

export default Routes;
