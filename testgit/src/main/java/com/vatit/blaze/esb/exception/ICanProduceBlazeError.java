package com.vatit.blaze.esb.exception;

import com.vatit.blaze.dto.BlazeError;

public interface ICanProduceBlazeError {
	BlazeError toBlazeError();
}
