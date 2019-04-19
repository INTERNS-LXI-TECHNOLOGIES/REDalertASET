import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alert.reducer';
import { IAlert } from 'app/shared/model/alert.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlertDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AlertDetail extends React.Component<IAlertDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { alertEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Alert [<b>{alertEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{alertEntity.type}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{alertEntity.description}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{alertEntity.status ? 'true' : 'false'}</dd>
            <dt>User Domain</dt>
            <dd>{alertEntity.userDomainId ? alertEntity.userDomainId : ''}</dd>
            <dt>Location</dt>
            <dd>{alertEntity.locationId ? alertEntity.locationId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/alert" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/alert/${alertEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ alert }: IRootState) => ({
  alertEntity: alert.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlertDetail);
