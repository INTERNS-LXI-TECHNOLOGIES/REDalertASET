import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/user-domain">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;User Domain
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/contact">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Contact
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/alert">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Alert
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/location">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Location
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/emergency-service">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Emergency Service
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/role">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Role
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
