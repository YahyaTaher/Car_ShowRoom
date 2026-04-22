let dynamicOptionsCache = null;

async function fetchDynamicOptions() {
    if (dynamicOptionsCache) return dynamicOptionsCache;

    const response = await fetch(`${API_BASE_URL}/options`, {
        headers: getAuthHeaders()
    });
    const data = await response.json();
    if (!data.success) {
        throw new Error(data.message || 'Failed to load options');
    }
    dynamicOptionsCache = data.data || {};
    return dynamicOptionsCache;
}

function fillSelect(selectEl, options, getValue, getLabel, defaultLabel) {
    if (!selectEl) return;
    const defaultOption = `<option value="">${defaultLabel}</option>`;
    const rendered = (options || []).map(item => `<option value="${getValue(item)}">${getLabel(item)}</option>`).join('');
    selectEl.innerHTML = defaultOption + rendered;
}

