import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emergency-service.reducer';
import { IEmergencyService } from 'app/shared/model/emergency-service.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmergencyServiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmergencyServiceDetail extends React.Component<IEmergencyServiceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { emergencyServiceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            EmergencyService [<b>{emergencyServiceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{emergencyServiceEntity.name}</dd>
            <dt>
              <span id="phone">Phone</span>
            </dt>
            <dd>{emergencyServiceEntity.phone}</dd>
            <dt>Alert</dt>
            <dd>{emergencyServiceEntity.alertId ? emergencyServiceEntity.alertId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/emergency-service" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/emergency-service/${emergencyServiceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ emergencyService }: IRootState) => ({
  emergencyServiceEntity: emergencyService.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmergencyServiceDetail);
