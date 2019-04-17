import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAlert } from 'app/shared/model/alert.model';
import { getEntities as getAlerts } from 'app/entities/alert/alert.reducer';
import { getEntity, updateEntity, createEntity, reset } from './emergency-service.reducer';
import { IEmergencyService } from 'app/shared/model/emergency-service.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmergencyServiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmergencyServiceUpdateState {
  isNew: boolean;
  alertId: string;
}

export class EmergencyServiceUpdate extends React.Component<IEmergencyServiceUpdateProps, IEmergencyServiceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      alertId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAlerts();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { emergencyServiceEntity } = this.props;
      const entity = {
        ...emergencyServiceEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/emergency-service');
  };

  render() {
    const { emergencyServiceEntity, alerts, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="redalertApp.emergencyService.home.createOrEditLabel">Create or edit a EmergencyService</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : emergencyServiceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="emergency-service-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel">Name</Label>
                  <AvInput
                    id="emergency-service-name"
                    type="select"
                    className="form-control"
                    name="name"
                    value={(!isNew && emergencyServiceEntity.name) || 'FIRE'}
                  >
                    <option value="FIRE">FIRE</option>
                    <option value="AMBULANCE">AMBULANCE</option>
                    <option value="POLICE">POLICE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    Phone
                  </Label>
                  <AvField id="emergency-service-phone" type="string" className="form-control" name="phone" />
                </AvGroup>
                <AvGroup>
                  <Label for="alert.id">Alert</Label>
                  <AvInput id="emergency-service-alert" type="select" className="form-control" name="alertId">
                    <option value="" key="0" />
                    {alerts
                      ? alerts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/emergency-service" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  alerts: storeState.alert.entities,
  emergencyServiceEntity: storeState.emergencyService.entity,
  loading: storeState.emergencyService.loading,
  updating: storeState.emergencyService.updating,
  updateSuccess: storeState.emergencyService.updateSuccess
});

const mapDispatchToProps = {
  getAlerts,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmergencyServiceUpdate);
