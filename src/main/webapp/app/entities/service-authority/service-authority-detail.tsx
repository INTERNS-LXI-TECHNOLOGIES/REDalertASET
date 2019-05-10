import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-authority.reducer';
import { IServiceAuthority } from 'app/shared/model/service-authority.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceAuthorityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ServiceAuthorityDetail extends React.Component<IServiceAuthorityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { serviceAuthorityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ServiceAuthority [<b>{serviceAuthorityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="districtName">District Name</span>
            </dt>
            <dd>{serviceAuthorityEntity.districtName}</dd>
            <dt>
              <span id="authorityName">Authority Name</span>
            </dt>
            <dd>{serviceAuthorityEntity.authorityName}</dd>
            <dt>
              <span id="phone">Phone</span>
            </dt>
            <dd>{serviceAuthorityEntity.phone}</dd>
          </dl>
          <Button tag={Link} to="/entity/service-authority" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/service-authority/${serviceAuthorityEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ serviceAuthority }: IRootState) => ({
  serviceAuthorityEntity: serviceAuthority.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceAuthorityDetail);
